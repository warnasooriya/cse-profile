import { RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';

import { PagesComponent } from './pages.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ECommerceComponent } from './e-commerce/e-commerce.component';
import { NotFoundComponent } from './miscellaneous/not-found/not-found.component';

const routes: Routes = [{
  path: '',
  component: PagesComponent,
  children: [
    {
      path: 'dashboard',
      component: ECommerceComponent,
    },
    {
      path: 'iot-dashboard',
      component: DashboardComponent,
    },

    {
      path: 'trade',
      loadChildren: () => import('./trades/trades.module')
        .then(m => m.TradesModule),
    },
    {
      path: 'divident',
      loadChildren: () => import('./divident/divident.module')
        .then(m => m.DividentModule),
    },
    {
      path: 'deposit',
      loadChildren: () => import('./diposit/diposit.module')
        .then(m => m.DipositModule),
    },
    {
      path: 'buy',
      loadChildren: () => import('./buy/buy.module')
        .then(m => m.BuyModule),
    },
    {
      path: 'sell',
      loadChildren: () => import('./sell/sell.module')
        .then(m => m.SellModule),
    },
    {
      path: 'transactions',
      loadChildren: () => import('./transactions/transactions.module')
        .then(m => m.TransactionsModule),
    },
    {
      path: 'profit',
      loadChildren: () => import('./profit/profit.module')
        .then(m => m.ProfitModule),
    },
    {
      path: 'editors',
      loadChildren: () => import('./editors/editors.module')
        .then(m => m.EditorsModule),
    },
    {
      path: 'tables',
      loadChildren: () => import('./tables/tables.module')
        .then(m => m.TablesModule),
    },
    {
      path: 'miscellaneous',
      loadChildren: () => import('./miscellaneous/miscellaneous.module')
        .then(m => m.MiscellaneousModule),
    },
    {
      path: '',
      redirectTo: 'dashboard',
      pathMatch: 'full',
    },
    {
      path: '**',
      component: NotFoundComponent,
    },
  ],
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PagesRoutingModule {
}