import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SplitRoutingModule } from './split-routing.module';
import { SplitComponent } from './split.component';
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
import { ThemeModule } from 'app/@theme/theme.module';
import { FormsModule as ngFormsModule, FormsModule,ReactiveFormsModule } from '@angular/forms';
import { Ng2SmartTableModule } from 'ng2-smart-table';
 

@NgModule({
  declarations: [SplitComponent],
  imports: [
    CommonModule,
    SplitRoutingModule,
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
  ]
})
export class SplitModule { }
