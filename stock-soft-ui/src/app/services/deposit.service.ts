import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DepositService {
  getPreviousDeposits() {
    let userId = localStorage.getItem("userId");
    let url = environment.endPointURL + "stock/getPreviousDeposits/"+userId;
    return this.http.get(url);
  }
  deleteDeposit(id: any) {
    let userId = localStorage.getItem("userId");
    let url = environment.endPointURL + "stock/deleteDeposit/"+id+"/"+userId;
    return this.http.delete(url);
  }
  loadDeposit(userId: string) {
    let url = environment.endPointURL + "stock/getAllDepositByUser/"+userId;
    return this.http.get(url);
  }
  saveDeposit(data:any) {
    let url = environment.endPointURL + "stock/saveDeposit";
    return this.http.post(url,data);
  }

  constructor(private http:HttpClient) { }
}
