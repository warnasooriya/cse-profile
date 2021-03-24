import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'ngx-loginout',
  templateUrl: './loginout.component.html',
  styleUrls: ['./loginout.component.scss']
})
export class LoginoutComponent implements OnInit {

  constructor( private router: Router) { }

  ngOnInit(): void {
    localStorage.removeItem('userId');
    localStorage.removeItem('refresh_token');
    localStorage.removeItem('access_token');
    this.router.navigate(['../auth']);
  }

}
