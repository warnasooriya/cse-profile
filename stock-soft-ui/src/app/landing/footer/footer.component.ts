import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'ngx-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  constructor() { }
  cYear = new Date().getFullYear()
  ngOnInit(): void {

  }

}
