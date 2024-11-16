import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { BuysService } from '../../../services/buys.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../../models/product';
import { Buys } from '../../../models/buys';
import { BuyDetail } from '../../../models/buydetail';
import { BuyDetailService } from '../../../services/buy-detail.service';

@Component({
  selector: 'app-add-detallecompra',
  templateUrl: './add-detallecompra.component.html',
  styleUrl: './add-detallecompra.component.css'
})
export class AddDetallecompraComponent {

  addEditDetallecompra!: FormGroup;
  products!: Product[];
  buyss!: Buys[];
  detallecompraId = 0;

  constructor(
    private productService: ProductService,
    private buysService: BuysService,
    private buyDetailService: BuyDetailService,
    private formBuilder: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.creaFormulario();
  }

  creaFormulario(): void {
    this.buysService.getBuys().subscribe({
      next: (data: Buys[]) => {
        this.buyss = data;
      }
    });

    this.productService.getProducts().subscribe({
      next: (data: Product[]) => {
        this.products = data;
      }
    });

    this.addEditDetallecompra = this.formBuilder.group({
      id: [''],
      quantity: ['', [Validators.required, Validators.min(1)]],
      subtotal: ['', [Validators.required, Validators.min(0)]],
      buysId: ['', Validators.required],
      productId: ['', Validators.required]
    });

    this.detallecompraId = parseInt(this.activatedRoute.snapshot.params['id']);
    if (this.detallecompraId == undefined || isNaN(this.detallecompraId)) {
      this.detallecompraId = 0;
    }

    if (this.detallecompraId > 0) {
      this.buyDetailService.getBuyDetail(this.detallecompraId).subscribe({
        next: (dataDetail: BuyDetail) => {
          this.addEditDetallecompra.get('id')?.setValue(dataDetail.id);
          this.addEditDetallecompra.get('quantity')?.setValue(dataDetail.quantity);
          this.addEditDetallecompra.get('subtotal')?.setValue(dataDetail.subtotal);
          this.addEditDetallecompra.get('buysId')?.setValue(dataDetail.buysId);
          this.addEditDetallecompra.get('productId')?.setValue(dataDetail.productId);
        }
      });
    }
  }

  grabarDetalleCompra(): void {
    const detalleCompra: BuyDetail = {
      id: this.detallecompraId,
      quantity: this.addEditDetallecompra.get('quantity')?.value,
      subtotal: this.addEditDetallecompra.get('subtotal')?.value,
      buysId: this.addEditDetallecompra.get('buysId')?.value,
      productId: this.addEditDetallecompra.get('productId')?.value
    };

    if (this.detallecompraId == 0) {
      this.buyDetailService.insertBuyDetail(detalleCompra).subscribe({
        next: () => {
          this.router.navigate(['/detalleCompra']);
          this.snackBar.open('Se registró correctamente el detalle de compra', 'OK', { duration: 2000 });
        },
        error: (err) => {
          this.snackBar.open('Error al registrar el detalle de compra', 'OK', { duration: 2000 });
          console.log(err);
        }
      });
    } else {
      this.buyDetailService.editarBuyDetail(detalleCompra).subscribe({
        next: () => {
          this.router.navigate(['/detalleCompra']);
          this.snackBar.open('Se actualizó correctamente el detalle de compra', 'OK', { duration: 2000 });
        },
        error: (err) => {
          this.snackBar.open('Error al actualizar el detalle de compra', 'OK', { duration: 2000 });
          console.log(err);
        }
      });
    }
  }
  
}
