import { Component } from '@angular/core';
import { ProductService } from '../../../services/product.service';
import { ProductReport} from '../../../models/product_report';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmarEliminarComponent } from '../../confirmaciones/confirmar-eliminar/confirmar-eliminar.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-list-producto',
  templateUrl: './list-producto.component.html',
  styleUrl: './list-producto.component.css'
})
export class ListProductoComponent {

  displayedColumns: string[] = ['id', 'name', 'categoryName', 'price', 'stock', 'opciones'];
  dsProductos = new MatTableDataSource<ProductReport>();

  constructor(
    private productService: ProductService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit() {
    this.cargarProductos();
  }

  cargarProductos() {
    this.productService.getProductReport().subscribe({
      next: (productos: ProductReport[]) => {

        
        this.dsProductos.data = productos;
      },
      error: () => {
        this.snackBar.open('Error al cargar productos', 'OK', { duration: 2000 });
      }
    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dsProductos.filter = filterValue.trim().toLowerCase();
  }

  onDelete(id: number) {
    const dialogRef = this.dialog.open(ConfirmarEliminarComponent);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.productService.deleteProduct(id).subscribe({
          next: () => {
            this.cargarProductos();
            this.snackBar.open('Producto eliminado correctamente.', 'OK', { duration: 2000 });
          },
          error: () => {
            this.snackBar.open('Error al eliminar el producto', 'OK', { duration: 2000 });
          }
        });
      }
    });
  }
  
}
