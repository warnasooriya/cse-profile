import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { IpoAndRightIssuesRoutingModule } from './ipo-and-right-issues-routing.module';
import { IpoAndRightIssuesComponent } from './ipo-and-right-issues.component';
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
  declarations: [IpoAndRightIssuesComponent],
  imports: [
    CommonModule,
    IpoAndRightIssuesRoutingModule,
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
export class IpoAndRightIssuesModule { }
