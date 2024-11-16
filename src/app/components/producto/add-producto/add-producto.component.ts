import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { CategoryService } from '../../../services/category.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from '../../../models/category';
import { Product } from '../../../models/product';

@Component({
  selector: 'app-add-producto',
  templateUrl: './add-producto.component.html',
  styleUrl: './add-producto.component.css'
})
export class AddProductoComponent {
 
  addEditProducto!: FormGroup;
  categories!: Category[];
  productoId = 0;

  //logo
  

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private formBuilder: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.creaFormulario();
    this.cargarCategorias();

    this.productoId = parseInt(this.activatedRoute.snapshot.params['id']);
    if (!this.productoId || isNaN(this.productoId)) {
      this.productoId = 0;
    }

    if (this.productoId > 0) {
      this.cargarProducto();
    }
  }

  creaFormulario(): void {
    this.addEditProducto = this.formBuilder.group({
      id: [''],
      name: ['', [Validators.required, Validators.minLength(3)]],
      categoryId: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      stock: ['', [Validators.required, Validators.min(0)]]
    });
  }

  cargarCategorias(): void {
    this.categoryService.getCategorys().subscribe({
      next: (data: Category[]) => {
        this.categories = data;
      },
      error: (err) => {
        console.error(err);
        this.snackBar.open('Error al cargar las categorías', 'OK', { duration: 2000 });
      }
    });
  }

  cargarProducto(): void {
    this.productService.getProduct(this.productoId).subscribe({
      next: (product: Product) => {
        this.addEditProducto.patchValue({
          id: product.id,
          name: product.name,
          categoryId: product.categoryId,
          price: product.price,
          stock: product.stock
        });
      },
      error: (err) => {
        console.error(err);
        this.snackBar.open('Error al cargar el producto', 'OK', { duration: 2000 });
      }
    });
  }

  grabarProducto(): void {
    const product: Product = {
      id: this.productoId,
      name: this.addEditProducto.get('name')?.value,
      categoryId: this.addEditProducto.get('categoryId')?.value,
      price: this.addEditProducto.get('price')?.value,
      stock: this.addEditProducto.get('stock')?.value
    };

    if (this.productoId === 0) {
      // Insertando producto
      this.productService.insertProduct(product).subscribe({
        next: () => {
          this.router.navigate(['/productos']);
          this.snackBar.open('Producto registrado exitosamente', 'OK', { duration: 2000 });
        },
        error: (err) => {
          console.error(err);
          this.snackBar.open('Error al registrar el producto', 'OK', { duration: 2000 });
        }
      });
    } else {
      // Actualizando producto
      this.productService.editarProduct(product).subscribe({
        next: () => {
          this.router.navigate(['/productos']);
          this.snackBar.open('Producto actualizado exitosamente', 'OK', { duration: 2000 });
        },
        error: (err) => {
          console.error(err);
          this.snackBar.open('Error al actualizar el producto', 'OK', { duration: 2000 });
        }
      });
    }
  }
  
}

