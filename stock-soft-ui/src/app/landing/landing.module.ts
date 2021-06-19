import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LandingRoutingModule } from './landing-routing.module';
import { LandingComponent } from './landing.component';
import { AboutusComponent } from './aboutus/aboutus.component';
import { FeaturesComponent } from './features/features.component';
import { FaqComponent } from './faq/faq.component';
import { ContactComponent } from './contact/contact.component';
import { NavComponent } from './nav/nav.component';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './footer/footer.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';




@NgModule({
  declarations: [LandingComponent, AboutusComponent, FeaturesComponent, FaqComponent, ContactComponent, NavComponent, HomeComponent, FooterComponent, LoginComponent, RegisterComponent],
  imports: [
    CommonModule,
    LandingRoutingModule,

  ]
})
export class LandingModule { }
