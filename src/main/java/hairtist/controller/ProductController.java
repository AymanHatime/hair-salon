package hairtist.controller;

import hairtist.dto.ProductDTO;
import hairtist.model.Product;
import hairtist.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping()
	public ResponseEntity<List<Product>> listAllProducts() {
		return ResponseEntity.ok(productRepository.findAll());
	}

	@GetMapping("/{id}")
	private ResponseEntity<Product> getProductById(@PathVariable UUID id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			return ResponseEntity.ok(product.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping()
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductDTO product) {
		Optional<Product> otherProduct = productRepository.findByName(product.getName());
		if(otherProduct.isPresent()) {
			return ResponseEntity.badRequest().build();
		}else{
			Product res = new Product(product.getName(), product.getDescription(), product.getPrice(), product.getDuration(), product.isVip(), product.isByPhone());
			productRepository.save(res);
			return ResponseEntity.ok(res);
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductDTO updatedProduct) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			if(!product.get().getName().equals(updatedProduct.getName())) {
				Optional<Product> otherProduct = productRepository.findByName(updatedProduct.getName());
				if(otherProduct.isPresent()) return ResponseEntity.badRequest().build();
			}
			product.get().setName(updatedProduct.getName());
			product.get().setDescription(updatedProduct.getDescription());
			product.get().setPrice(updatedProduct.getPrice());
			product.get().setDuration(updatedProduct.getDuration());
			product.get().setVip(updatedProduct.isVip());
			product.get().setByPhone(updatedProduct.isByPhone());
			productRepository.save(product.get());
			return  ResponseEntity.ok(product.get());
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable UUID id) {
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent()) {
			productRepository.delete(product.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
}
