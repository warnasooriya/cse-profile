import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CalculatorRoutingModule } from './calculator-routing.module';
import { CalculatorComponent } from './calculator.component';
import {
  NbInputModule,
  NbCardModule,
  NbButtonModule,
  NbActionsModule,
  NbUserModule,
  NbCheckboxModule,
  NbDatepickerModule,
  NbSelectModule,
  NbIconModule,
  NbAlertModule,
  NbAutocompleteModule,
  NbRouteTabsetModule,
  NbTabsetModule,
  NbBadgeModule
} from '@nebular/theme';
import { Select2Module } from 'ng-select2-component';

@NgModule({
  declarations: [CalculatorComponent],
  imports: [
    CommonModule,
    CalculatorRoutingModule,
    NbInputModule,
    NbCardModule,
    NbButtonModule,
    NbActionsModule,
    NbUserModule,
    NbCheckboxModule,
    NbCardModule,
    NbDatepickerModule,
    NbSelectModule,
    NbIconModule,
    NbAlertModule,
    NbAutocompleteModule,
    Select2Module,
    NbRouteTabsetModule,
    NbTabsetModule,
    NbBadgeModule
  ]
})
export class CalculatorModule { }
