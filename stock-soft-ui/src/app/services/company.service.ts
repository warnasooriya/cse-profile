import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { HttpClient } from '@angular/common/http';
 

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  getCommission() {
    let url = environment.endPointURL + "company/getCommission";
    return this.http.get(url);
  }
  constructor(private http:HttpClient) { }
  getCompanies() {
    let url = environment.endPointURL + "company/getAll";
    return this.http.get(url);
  }

 
}
