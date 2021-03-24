import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class DividentService {
  deleteDividend(id) {
    let userId = localStorage.getItem("userId");
    let url = environment.endPointURL + "stock/deleteDividend/"+id+"/"+userId;
    return this.http.delete(url);
  }
  loadDividendToTable(userId: string) {
    let url = environment.endPointURL + "stock/loadDividendToTable/"+userId;
    return this.http.get(url);
  }
 
 
  saveDivident(data: any) {
    console.log(data);
    let url = environment.endPointURL + "stock/saveDividend";
    return this.http.post(url,data);
  }

  constructor(private http:HttpClient) { }
}
