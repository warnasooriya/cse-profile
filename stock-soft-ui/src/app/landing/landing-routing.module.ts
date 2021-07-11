import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './register/register.component';
import { AboutusComponent } from './aboutus/aboutus.component';
import { ContactComponent } from './contact/contact.component';
import { FaqComponent } from './faq/faq.component';
import { FeaturesComponent } from './features/features.component';

import { LandingComponent } from './landing.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  { path: 'about', component: AboutusComponent },
  { path: 'features', component: FeaturesComponent },
  { path: 'faq', component: FaqComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'home', component: LandingComponent },
  { path: 'login', component: LoginComponent },
  { path: 'start-now', component: RegisterComponent },
  { path: '', component: LandingComponent },
  { path: '**', component: LandingComponent },

];

@NgModule({
  imports: [RouterModule.forChild(routes,)],
  exports: [RouterModule]
})

export class LandingRoutingModule { }
