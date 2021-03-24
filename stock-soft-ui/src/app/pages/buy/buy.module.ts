import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
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
import { BuyRoutingModule } from './buy-routing.module';
import { BuyComponent } from './buy.component';
import { ThemeModule } from '../../@theme/theme.module';
import { FormsModule as ngFormsModule, FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [BuyComponent],
  imports: [
    CommonModule,
    BuyRoutingModule,
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
export class BuyModule { 

}
