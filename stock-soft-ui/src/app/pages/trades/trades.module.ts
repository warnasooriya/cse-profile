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
     NbTooltipModule,
     
    } from '@nebular/theme';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { TradesRoutingModule  } from './trades-routing.module';
import { TradesComponent } from './trades.component';
import { ThemeModule } from '../../@theme/theme.module';
import { FsIconComponent } from './trades.component';
import { FormsModule, ReactiveFormsModule   } from '@angular/forms';
import { NgxEchartsModule } from 'ngx-echarts';
import { NgxChartsModule } from '@swimlane/ngx-charts';

@NgModule({
  declarations: [TradesComponent,FsIconComponent],
  imports: [
    CommonModule,
    TradesRoutingModule,
    NbCardModule,
    NbTreeGridModule,
    NbIconModule,
    NbInputModule,
    ThemeModule,
    Ng2SmartTableModule,
    NbAlertModule,
    NbButtonModule,
    NbDatepickerModule,
    NbSelectModule,
    NbUserModule,
    NbActionsModule,
    NbRadioModule,
    NbAutocompleteModule,
    FormsModule,
    ReactiveFormsModule,
    NbTooltipModule,
    NgxEchartsModule,
    NgxChartsModule,
  ]
})
export class TradesModule { }
