import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProfitComponent } from './profit.component';

const routes: Routes = [{ path: '', component: ProfitComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfitRoutingModule { }
