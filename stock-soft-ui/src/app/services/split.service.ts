import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SplitService {
  loadCurrentAvgPrice(userId: string) {
    let url = environment.endPointURL + "stock/getAvailableStock/" + userId;
    return this.http.get(url);
  }
  loadCurrentAvailability(userId: string, companyId: string) {
    let url = environment.endPointURL + "stock/getAvailableStockDetails/" + userId + "/" + companyId;
    return this.http.get(url);
  }
  deleteSplit(id) {
    let userId = localStorage.getItem("userId");
    let url = environment.endPointURL + "stock/deleteSplit/" + id + "/" + userId;
    return this.http.delete(url);
  }

  loadSplitedDataToTable(userId: string) {
    let url = environment.endPointURL + "stock/loadSplitedDataToTable/" + userId;
    return this.http.get(url);
  }

  saveSplit(data: any) {
    console.log(data);
    let url = environment.endPointURL + "stock/splitStock";
    return this.http.post(url, data);
  }



  constructor(private http: HttpClient) { }
}
