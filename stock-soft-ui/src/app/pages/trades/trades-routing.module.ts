import { NgModule, OnDestroy } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TradesComponent } from './trades.component';
import { NbThemeService } from '@nebular/theme';
import { SolarData } from 'app/@core/data/solar';
import { takeWhile } from 'rxjs/operators';

const routes: Routes = [{ path: '', component: TradesComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TradesRoutingModule  {
 

 }
