import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DipositComponent } from './diposit.component';

const routes: Routes = [{ path: '', component: DipositComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DipositRoutingModule { }
