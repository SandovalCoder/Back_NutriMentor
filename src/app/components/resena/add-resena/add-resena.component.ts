import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { ClientService } from '../../../services/client.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../../models/product';
import { Client } from '../../../models/client';
import { Review } from '../../../models/review';
import { ReviewService } from '../../../services/review.service';


@Component({
  selector: 'app-add-resena',
  templateUrl: './add-resena.component.html',
  styleUrl: './add-resena.component.css'
})
export class AddResenaComponent {

  addEditResena!: FormGroup;
  products!: Product[];
  clients!: Client[];
  resenaId = 0;

  constructor(
    private productService: ProductService,
    private clientService: ClientService,
    private reviewService: ReviewService,
    private formBuilder: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {
    this.ngOnInit();
  }

  ngOnInit(): void {
    this.creaFormulario();
  }

  creaFormulario(): void {
    this.clientService.getClients().subscribe({
      next: (data: Client[]) => {
        this.clients = data;
      }
    });

    this.productService.getProducts().subscribe({
      next: (data: Product[]) => {
        this.products = data;
      }
    });

    this.addEditResena = this.formBuilder.group({
      id: [''],
      score: ['', [Validators.required, Validators.min(1), Validators.max(5)]],
      comment: ['', [Validators.required, Validators.minLength(5)]],
      productId: ['', Validators.required],
      clientId: ['', Validators.required]
    });

    this.resenaId= parseInt(this.activatedRoute.snapshot.paramMap.get('id')!);
    if(this.resenaId==undefined || isNaN(this.resenaId)){
      this.resenaId=0;
    }

    if(this.resenaId>0){
      this.reviewService.getReview(this.resenaId).subscribe({
        next: (dataReview: Review) => {
          this.addEditResena.get('id')?.setValue(dataReview.id);
          this.addEditResena.get('score')?.setValue(dataReview.score);
          this.addEditResena.get('comment')?.setValue(dataReview.comment);
          this.addEditResena.get('productId')?.setValue(dataReview.productId);
          this.addEditResena.get('clientId')?.setValue(dataReview.clientId);
        }
      });
    }
  }

  grabarResenas(): void {
    if (this.addEditResena.invalid) {
      return;
    }

    const resena: Review = {
      id: this.addEditResena.value.id,
      score: this.addEditResena.value.score,
      comment: this.addEditResena.value.comment,
      productId: this.addEditResena.value.productId,
      clientId: this.addEditResena.value.clientId
    };

    if (this.resenaId === 0) {
      this.reviewService.insertReview(resena).subscribe({
        next: () => {
          this.snackBar.open('Resena guardada correctamente.', 'OK', { duration: 2000 });
          this.router.navigate(['/resenas']);
        },
        error: () => {
          this.snackBar.open('Error al guardar la resena', 'OK', { duration: 2000 });
        }
      });
    } else {
      this.reviewService.editarReview(resena).subscribe({
        next: () => {
          this.snackBar.open('Resena editada correctamente.', 'OK', { duration: 2000 });
          this.router.navigate(['/resenas']);
        },
        error: () => {
          this.snackBar.open('Error al editar la resena', 'OK', { duration: 200});
        }
      });
    }
  }
  
}
