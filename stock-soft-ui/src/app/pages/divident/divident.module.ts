import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DividentRoutingModule } from './divident-routing.module';
import { DividentComponent } from './divident.component';
import { ThemeModule } from 'app/@theme/theme.module';
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
  NbAutocompleteModule
} from '@nebular/theme';

import { FormsModule as ngFormsModule, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { Select2Module } from 'ng-select2-component';

@NgModule({
  declarations: [DividentComponent],
  imports: [
    CommonModule,
    DividentRoutingModule,
    ThemeModule,
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
    ngFormsModule,
    FormsModule,
    ReactiveFormsModule,
    Ng2SmartTableModule,
    Select2Module

  ]
})
export class DividentModule { }
