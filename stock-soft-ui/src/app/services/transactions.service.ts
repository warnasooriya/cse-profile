import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TransactionsService {
  
  getTransactions(data: any) {
    let url = environment.endPointURL + "transactions/getTransactions";
    return this.http.post(url,data);
  }

  constructor(private http:HttpClient) { }
}
