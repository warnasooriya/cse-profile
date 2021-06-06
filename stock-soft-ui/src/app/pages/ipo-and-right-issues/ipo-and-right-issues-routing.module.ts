import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { IpoAndRightIssuesComponent } from './ipo-and-right-issues.component';

const routes: Routes = [{ path: '', component: IpoAndRightIssuesComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IpoAndRightIssuesRoutingModule { }
