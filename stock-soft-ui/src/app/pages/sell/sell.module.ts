import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SellRoutingModule } from './sell-routing.module';
import { SellComponent } from './sell.component';
 
import { NbCardModule, 
  NbIconModule, 
  NbInputModule,
   NbTreeGridModule,
    NbAlertModule, 
    NbButtonModule, 
     NbDatepickerModule ,
     NbRadioModule,
     NbSelectModule,
     NbUserModule,
     NbActionsModule,
     NbAutocompleteModule,
     
    } from '@nebular/theme';
    import { Ng2SmartTableModule } from 'ng2-smart-table';
import { ThemeModule } from 'app/@theme/theme.module';
import { FormsModule as ngFormsModule, FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [SellComponent],
  imports: [
    CommonModule,
    SellRoutingModule,
    NbCardModule,
    NbTreeGridModule,
    NbIconModule,
    NbInputModule,
    ThemeModule,
    Ng2SmartTableModule,
    NbAlertModule,
    NbButtonModule,
    NbDatepickerModule,
    ngFormsModule,
    NbSelectModule,
    NbUserModule,
    NbActionsModule,
    NbRadioModule,
    NbAutocompleteModule,
    FormsModule,
    ReactiveFormsModule,
  ]
})
export class SellModule { }
