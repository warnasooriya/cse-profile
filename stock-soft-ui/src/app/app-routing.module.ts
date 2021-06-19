import { ExtraOptions, RouterModule, Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import {
  NbAuthComponent,
  NbLoginComponent,
  NbLogoutComponent,
  NbRegisterComponent,
  NbRequestPasswordComponent,
  NbResetPasswordComponent,
} from '@nebular/auth';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { LoginoutComponent } from './loginout/loginout.component';
import { RegisterComponent } from './register/register.component';
import { AboutusComponent } from './landing/aboutus/aboutus.component';

export const routes: Routes = [

  {
    path: 'pages',
    loadChildren: () => import('./pages/pages.module')
      .then(m => m.PagesModule),
  },
  {
    path: 'auth',
    component: NbAuthComponent,
    children: [
      {
        path: '',
        component: LoginComponent,
      },
      {
        path: 'login',
        component: LoginComponent,
      },
      {
        path: 'register',
        component: RegisterComponent,
      },
      {
        path: 'logout',
        component: LoginoutComponent,
      },
      {
        path: 'request-password',
        component: NbRequestPasswordComponent,
      },
      {
        path: 'reset-password',
        component: NbResetPasswordComponent,
      },
    ],
  },


  { path: 'pages/trades', loadChildren: () => import('./pages/trades/trades.module').then(m => m.TradesModule) },
  { path: 'pages/divident', loadChildren: () => import('./pages/divident/divident.module').then(m => m.DividentModule) },
  { path: 'pages/diposit', loadChildren: () => import('./pages/diposit/diposit.module').then(m => m.DipositModule) },
  { path: 'pages/buy', loadChildren: () => import('./pages/buy/buy.module').then(m => m.BuyModule) },
  { path: 'pages/sell', loadChildren: () => import('./pages/sell/sell.module').then(m => m.SellModule) },
  { path: 'pages/transactions', loadChildren: () => import('./pages/transactions/transactions.module').then(m => m.TransactionsModule) },
  { path: 'pages/profit', loadChildren: () => import('./pages/profit/profit.module').then(m => m.ProfitModule) },
  { path: 'landing', loadChildren: () => import('./landing/landing.module').then(m => m.LandingModule) },
  //{ path: 'about', loadChildren: () => import('./landing/landing.module').then(m => m.LandingModule) },
  { path: 'pages/profile', loadChildren: () => import('./pages/profile/profile.module').then(m => m.ProfileModule) },
  { path: 'pages/split', loadChildren: () => import('./pages/split/split.module').then(m => m.SplitModule) },
  { path: 'pages/ipoRightIssues', loadChildren: () => import('./pages/ipo-and-right-issues/ipo-and-right-issues.module').then(m => m.IpoAndRightIssuesModule) },

  { path: 'pages/cash-withdraw', loadChildren: () => import('./pages/cash-withdraw/cash-withdraw.module').then(m => m.CashWithdrawModule) },

  { path: 'landing/about', component: AboutusComponent },
  { path: '', redirectTo: 'landing', pathMatch: 'full' },
  { path: '**', redirectTo: 'landing', pathMatch: 'full' },
];



@NgModule({
  imports: [CommonModule, RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
