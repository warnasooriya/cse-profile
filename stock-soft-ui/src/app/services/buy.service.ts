import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BuyService {
  deleteBuy(id: any) {
    let userId = localStorage.getItem("userId");
    let url = environment.endPointURL + "purchase/delete/"+id+"/"+userId;
    return this.http.delete(url);
  }

  getAvailableStockDetailsByCompany(selCompany: string) {
     let url = environment.endPointURL + "stock/getAvailableStockDetails/"+localStorage.getItem("userId")+"/"+selCompany;
    return this.http.get(url);
  }

  constructor(private http:HttpClient) { }

  getAvailableStock() {
   let url = environment.endPointURL + "stock/getAvailableStock/"+localStorage.getItem("userId");
    return this.http.get(url);
  }
  
  saveBuy(data: any) {
    let url = environment.endPointURL + "purchase/new";
    return this.http.post(url,data);
  }

  getBuyData(userId,data) {
    data["userId"]=userId;
    let url = environment.endPointURL + "purchase/getByUserId";
    console.log(data);
    return this.http.post(url,data);
  }

 
}
