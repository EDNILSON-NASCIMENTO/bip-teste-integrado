import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { AppComponent } from './app/app.component';
import { provideAnimations } from '@angular/platform-browser/animations';

bootstrapApplication(AppComponent, {
  providers: [
    // 2. Adicione o provedor de formulários
    provideAnimations(),
    provideHttpClient(withInterceptorsFromDi())
  ]
}).catch(err => console.error(err));