package com.example.e_commerce_app_backend.service;

import com.example.e_commerce_app_backend.Exception.CategoryNotFoundException;
import com.example.e_commerce_app_backend.Exception.ProductNotFoundException;
import com.example.e_commerce_app_backend.dtos.ProductImageResponseDTO;
import com.example.e_commerce_app_backend.dtos.ProductRequestDTO;
import com.example.e_commerce_app_backend.dtos.ProductResponseDTO;
import com.example.e_commerce_app_backend.entities.*;
import com.example.e_commerce_app_backend.enums.Color;
import com.example.e_commerce_app_backend.enums.ProductStatus;
import com.example.e_commerce_app_backend.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    private final ProductColorRepository productColorRepository;
    private final ProductSizeRepository productSizeRepository;
    private final ProductImageRepository productImageRepository;
    final private ProductRepository productRepository;
    final private CategoryRepository categoryRepository;
    final private BrandRepository brandRepository;
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, BrandRepository brandRepository, ProductColorRepository productColorRepository, ProductSizeRepository productSizeRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productColorRepository = productColorRepository;
        this.productSizeRepository = productSizeRepository;
        this.productImageRepository = productImageRepository;
    }

    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<Product> products=  productRepository.findAll();
        List<ProductResponseDTO> dtos=new ArrayList<>();
        products.forEach(p->{
            dtos.add(p.toDTO());
        });
        return  ResponseEntity.ok(dtos);
    }
    public ResponseEntity<ProductResponseDTO> updateProductStatus(Long productId,ProductStatus status){
        Product product=productRepository.findById(productId).orElse(null);
        if(product==null){

        }
        product.setProductStatus(status);
        return  ResponseEntity.ok(productRepository.save(product).toDTO());
    }
    public  ResponseEntity<ProductResponseDTO> getProductById(Long id){
        Product product=  productRepository.findById(id).orElse(null);
        if(product==null){
            throw new ProductNotFoundException("product not found with id "+id);

        }
        return  ResponseEntity.ok(product.toDTO());
    }

    public ResponseEntity<List<ProductResponseDTO>> getCategoryProducts(Long categoryId){
        List<Product> products=  productRepository.findAllByCategoryId(categoryId);
        List<ProductResponseDTO> dtos=new ArrayList<>();
        if(products!=null) {
            products.forEach(p -> {
                dtos.add(p.toDTO());
            });
        }
        return  ResponseEntity.ok(dtos);
    }


    public ResponseEntity<List<ProductResponseDTO>> getProductsWithPriceLessThan(double max){
        List<Product> products=  productRepository.findAllByPriceLessThan(max);
        List<ProductResponseDTO> dtos=new ArrayList<>();
        if(products!=null) {
            products.forEach(p -> {
                dtos.add(p.toDTO());
            });
        }
        return  ResponseEntity.ok(dtos);
    }

    public ResponseEntity<List<ProductResponseDTO>> getProductsWithPriceBetween(double min,double max){
        List<Product> products=  productRepository.findAllByPriceBetween(min,max);
        List<ProductResponseDTO> dtos=new ArrayList<>();
        if(products!=null) {
            products.forEach(p -> {
                dtos.add(p.toDTO());
            });
        }
        return  ResponseEntity.ok(dtos);
    }



    public ResponseEntity<List<ProductResponseDTO>> getProductsThanContainTheName(String name){
        List<Product> products=  productRepository.findAllByNameIsContainingIgnoreCase(name);
        List<ProductResponseDTO> dtos=new ArrayList<>();
        products.forEach(p->{
            dtos.add(p.toDTO());
        });
        return  ResponseEntity.ok(dtos);
    }


    public ResponseEntity<List<ProductResponseDTO>> getProductsWithStatus(ProductStatus productStatus){
        List<Product> products=  productRepository.findAllByProductStatus(productStatus);
        List<ProductResponseDTO> dtos=new ArrayList<>();
        products.forEach(p->{
            dtos.add(p.toDTO());
        });
        return  ResponseEntity.ok(dtos);
    }

    public ResponseEntity<List<ProductResponseDTO>> getProductsWithNameContainsAndStatus(String name,ProductStatus productStatus){
        List<Product> products=productRepository.findAllByNameIsContainingIgnoreCaseAndProductStatus(name,productStatus);
        List<ProductResponseDTO> dtos=new ArrayList<>();
        if(products!=null) {
            products.forEach(p -> {
                dtos.add(p.toDTO());
            });
        }
        return  ResponseEntity.ok(dtos);
    }


    public ResponseEntity<ProductResponseDTO> saveProduct(ProductRequestDTO productRequestDTO) throws IOException {
        Category category=categoryRepository.findById(productRequestDTO.getCategoryId()).orElse(null);
        if(category==null){
            throw  new CategoryNotFoundException("category not found with id "+productRequestDTO.getCategoryId());
        }
        Path path= Paths.get(System.getProperty("user.home"),"e-commerce-app","product_images");
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        Product product=Product.builder()
                .brand(brandRepository.findById(productRequestDTO.getBrandId()).orElseThrow(()-> new RuntimeException("brand not found")))
                .name(productRequestDTO.getName())
                .productStatus(ProductStatus.IN_STOCK)
                .category(category)
                .oldPrice(productRequestDTO.getOldPrice())
                .description(productRequestDTO.getDescription())
                .stock(productRequestDTO.getStock())
                .price(productRequestDTO.getPrice())
                .build();

        Product savedProduct=productRepository.save(product);

        List<ProductImage> productImageList=new ArrayList<>();
        productRequestDTO.getProductImageList().forEach(pi->{
            String fileId= UUID.randomUUID().toString();
            String fileExtension=pi.getOriginalFilename().substring(pi.getOriginalFilename().lastIndexOf("."));
            Path filePath=Paths.get(System.getProperty("user.home"),"e-commerce-app","product_images",fileId+fileExtension);
            try {
                Files.copy(pi.getInputStream(),filePath);
                ProductImage productImage=ProductImage.builder()
                        .imageURL(filePath.toUri().toString())
                        .product(savedProduct)
                        .build();
                ProductImage savedProductImage=productImageRepository.save(productImage);
                productImageList.add(savedProductImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        List<ProductColor> productColorList=new ArrayList<>();
        productRequestDTO.getProductColorList().forEach(colorId->{
            ProductColor productColor=productColorRepository.findById(colorId).get();
            productColorList.add(productColor);
        });

        List<ProductSize> productSizeList=new ArrayList<>();
        productRequestDTO.getProductSizeList().forEach(sizeId->{
            ProductSize productSize=productSizeRepository.findById(sizeId).get();
            productSizeList.add(productSize);
        });

        savedProduct.setProductColors(productColorList);
        savedProduct.setProductImageList(productImageList);
        savedProduct.setProductSizeList(productSizeList);
        return  ResponseEntity.ok(productRepository.save(savedProduct).toDTO());


    }


//    public ResponseEntity<ProductResponseDTO> updateProduct(Long productId,ProductRequestDTO productRequestDTO) throws IOException {
//        Product product=productRepository.findById(productId).orElse(null);
//        if(product == null) {
//            throw  new CategoryNotFoundException("category not found with id"+productRequestDTO.getCategoryId());
//        }
//        Category category=categoryRepository.findById(productRequestDTO.getCategoryId()).get();
//        Path path= Paths.get(System.getProperty("user.home"),"e-commerce-app","product_images");
//        if(!Files.exists(path)){
//            Files.createDirectories(path);
//        }
//        String fileId= UUID.randomUUID().toString();
//        Path filePath=Paths.get(System.getProperty("user.home"),"e-commerce-app","product_images",fileId+".png");
//        Files.copy(productRequestDTO.getImageURL().getInputStream(),filePath);
//        product.setProductStatus(productRequestDTO.getStatus());
//        product.setName(productRequestDTO.getName());
//        product.setStock(productRequestDTO.getStock());
//        product.setPrice(productRequestDTO.getPrice());
//        product.setDescription(productRequestDTO.getDescription());
//        product.setCategory(category);
//        product.setOldPrice(productRequestDTO.getOldPrice());
//        product.setBrand(productRequestDTO.getBrand());
//        product.setColor(productRequestDTO.getColor());
//        Files.delete(Paths.get(System.getProperty("user.home"),"e-commerce-app","product_images",product.getImageURL()+".png"));
//        product.setImageURL(Files.readString(filePath));
//        return  ResponseEntity.ok(productRepository.save(product).toDTO());
//
//
//    }

    public  void deleteProductById(Long id) throws IOException {
        Product product=productRepository.findById(id).orElse(null);
        if(product==null){
            throw new ProductNotFoundException("product not found with id "+id);
        }
        List<ProductImage> productImageResponseDTOS=product.getProductImageList();
        productImageResponseDTOS.forEach(p->{
            try {
                Files.delete(Path.of(URI.create(p.getImageURL())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        productRepository.deleteById(id);
    }



    public ResponseEntity<List<ProductResponseDTO>> getAllProductsWithCategoryIdAndBrandId(Long categoryId, Long brandId){
        List<Product> products=  productRepository.findAllByCategoryIdAndBrandId(categoryId,brandId);
        List<ProductResponseDTO> dtos=new ArrayList<>();
        if(products!=null) {
            products.forEach(p -> {
                dtos.add(p.toDTO());
            });
        }
        return  ResponseEntity.ok(dtos);
    }



//    public ResponseEntity<List<ProductResponseDTO>> getAllProductsWithCategoryIdAndBrandAndPriceLessThan(Long categoryId, Long brandId,Color color,double maxprice){
//        List<Product> products=  productRepository.findAllByCategoryIdAndBrandIdAndColorAndPriceLessThanEqual(categoryId,brandId,color,maxprice);
//        List<ProductResponseDTO> dtos=new ArrayList<>();
//        if(products!=null) {
//            products.forEach(p -> {
//                dtos.add(p.toDTO());
//            });
//        }
//        return  ResponseEntity.ok(dtos);
//    }
//
//
//    public ResponseEntity<List<ProductResponseDTO>> getAllProductsWithCategoryIdAndBrandIdAndColor(Long categoryId, Long brandId, Color color) {
//        List<Product> products=  productRepository.findAllByCategoryIdAndBrandIdAndColor(categoryId,brandId,color);
//        List<ProductResponseDTO> dtos=new ArrayList<>();
//        if(products!=null) {
//            products.forEach(p -> {
//                dtos.add(p.toDTO());
//            });
//        }
//        return  ResponseEntity.ok(dtos);
//    }
}
