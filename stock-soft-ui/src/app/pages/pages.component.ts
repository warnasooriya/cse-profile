import { Component, OnInit } from '@angular/core';

import { MENU_ITEMS } from './pages-menu';
import { Router } from '@angular/router';

@Component({
  selector: 'ngx-pages',
  styleUrls: ['pages.component.scss'],
  template: `
    <ngx-one-column-layout>
      <nb-menu [items]="menu"></nb-menu>
      <router-outlet></router-outlet>
    </ngx-one-column-layout>
  `,
})
export class PagesComponent implements OnInit{
  constructor( 
    private router: Router
  ) {}
  menu = MENU_ITEMS;

  
  ngOnInit() {
    console.log('pages');
    var user = localStorage.getItem('userId');
    if(user==undefined || user==null || user==""){
      localStorage.removeItem('userId');
      localStorage.removeItem('refresh_token');
      localStorage.removeItem('access_token');
      this.router.navigate(['../auth']);

    }
  }

}
