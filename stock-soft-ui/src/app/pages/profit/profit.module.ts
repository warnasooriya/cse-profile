import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfitRoutingModule } from './profit-routing.module';
import { ProfitComponent } from './profit.component';
import { ThemeModule } from 'app/@theme/theme.module';
import { NbActionsModule, NbAutocompleteModule, NbButtonModule, NbCardModule, NbCheckboxModule, NbDatepickerModule, NbIconModule, NbInputModule, NbSelectModule, NbUserModule } from '@nebular/theme';
import { FormsModule } from '../forms/forms.module';
import { ReactiveFormsModule } from '@angular/forms';
import { Ng2SmartTableModule } from 'ng2-smart-table';


@NgModule({
  declarations: [ProfitComponent],
  imports: [
    CommonModule,
    ProfitRoutingModule,
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
    NbCardModule,
    NbAutocompleteModule,
    FormsModule,
    ReactiveFormsModule,
    Ng2SmartTableModule,
  ]
})
export class ProfitModule { }
