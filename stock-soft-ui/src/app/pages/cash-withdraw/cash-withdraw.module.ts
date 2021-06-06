import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CashWithdrawRoutingModule } from './cash-withdraw-routing.module';
import { CashWithdrawComponent } from './cash-withdraw.component';
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

@NgModule({
  declarations: [CashWithdrawComponent],
  imports: [
    CommonModule,
    CashWithdrawRoutingModule,
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
export class CashWithdrawModule { }
