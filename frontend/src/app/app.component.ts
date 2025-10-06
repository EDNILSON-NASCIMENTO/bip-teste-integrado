import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Beneficio, BeneficioDTO, BeneficioService } from './beneficio.service';
import { BeneficioFormComponent } from './beneficio-form/beneficio-form.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, BeneficioFormComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  title = 'Desafio Fullstack - Benefícios';
  beneficios: Beneficio[] = [];
  isLoading = true;
  error: string | null = null;

  // Estado da UI
  mostrarFormulario = false;
  beneficioSelecionado: BeneficioDTO | null = null;

  constructor(private beneficioService: BeneficioService) {}

  ngOnInit(): void {
    this.carregarBeneficios();
  }

  carregarBeneficios(): void {
    this.isLoading = true;
    this.error = null;
    this.beneficioService.getBeneficios().subscribe({
      next: (data) => {
        this.beneficios = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Falha ao buscar benefícios:', err);
        this.error = 'Não foi possível carregar os dados. Verifique se o backend está em execução.';
        this.isLoading = false;
      }
    });
  }

  onNovoBeneficio(): void {
    this.beneficioSelecionado = null;
    this.mostrarFormulario = true;
  }

  onEditar(beneficio: Beneficio): void {
    this.beneficioSelecionado = { ...beneficio }; // Clona o objeto para evitar mutação direta
    this.mostrarFormulario = true;
  }

  onExcluir(id: number): void {
    if (confirm('Tem certeza que deseja excluir este benefício?')) {
      this.beneficioService.deleteBeneficio(id).subscribe({
        next: () => {
          this.carregarBeneficios(); // Recarrega a lista
        },
        error: (err) => {
          this.error = 'Falha ao excluir o benefício.';
          console.error(err);
        }
      });
    }
  }

  onFormSave(beneficioDTO: BeneficioDTO): void {
    const saveObservable = beneficioDTO.id
      ? this.beneficioService.updateBeneficio(beneficioDTO.id, beneficioDTO)
      : this.beneficioService.createBeneficio(beneficioDTO);

    saveObservable.subscribe({
      next: () => {
        this.mostrarFormulario = false;
        this.carregarBeneficios(); // Recarrega a lista
      },
      error: (err) => {
        this.error = 'Falha ao salvar o benefício.';
        console.error(err);
      }
    });
  }

  onFormCancel(): void {
    this.mostrarFormulario = false;
    this.beneficioSelecionado = null;
  }
}