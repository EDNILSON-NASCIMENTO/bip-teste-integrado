import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { BeneficioDTO } from '../beneficio.service';

@Component({
  selector: 'app-beneficio-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './beneficio-form.component.html',
  styleUrls: ['./beneficio-form.component.css']
})
export class BeneficioFormComponent implements OnInit, OnChanges {
  
  @Input() beneficio: BeneficioDTO | null = null;
  
  @Output() save = new EventEmitter<BeneficioDTO>();
  
  @Output() cancel = new EventEmitter<void>();

  form: FormGroup;
  isEditMode = false;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      nome: ['', Validators.required],
      descricao: [''],
      valor: [0, [Validators.required, Validators.min(0.01)]],
      ativo: [true]
    });
  }

  ngOnInit(): void {
    this.updateForm();
  }

  
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['beneficio']) {
      this.updateForm();
    }
  }

  private updateForm(): void {
    if (this.beneficio && this.beneficio.id) {
      this.isEditMode = true;
      this.form.patchValue({
        nome: this.beneficio.nome,
        descricao: this.beneficio.metadata?.['DESCRICAO'],
        valor: this.beneficio.metadata?.['VALOR'],
        ativo: this.beneficio.metadata?.['ATIVO'] === 'TRUE'
      });
    } else {
      this.isEditMode = false;
      this.form.reset({ ativo: true, valor: 0 });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    const formValue = this.form.value;

    const beneficioDTO: BeneficioDTO = {
      id: this.beneficio?.id,
      nome: formValue.nome,
      metadata: {
        DESCRICAO: formValue.descricao,
        VALOR: String(formValue.valor),
        ATIVO: String(formValue.ativo).toUpperCase()
      }
    };

    this.save.emit(beneficioDTO);
  }

  onCancel(): void {
    this.cancel.emit();
  }
}