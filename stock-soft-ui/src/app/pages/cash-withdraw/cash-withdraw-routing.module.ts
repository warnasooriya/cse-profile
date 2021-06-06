import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CashWithdrawComponent } from './cash-withdraw.component';

const routes: Routes = [{ path: '', component: CashWithdrawComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CashWithdrawRoutingModule { }
