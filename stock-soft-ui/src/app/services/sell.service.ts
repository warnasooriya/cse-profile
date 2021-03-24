import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SellService {
  deleteSales(id: any) {
    let userId = localStorage.getItem("userId");
    let url = environment.endPointURL + "sales/delete/"+id+"/"+userId;
    return this.http.delete(url);
  }
  saveSell(formData: any) {
      let url = environment.endPointURL + "sales/new";
      return this.http.post(url,formData);
  }
  
  constructor(private http:HttpClient) { }
  getSellData( data:any) {
      let url = environment.endPointURL + "sales/getByUserId";
    return this.http.post(url,data);
  }

 
}
