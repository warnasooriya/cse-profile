import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root'
})
export class IporiService {
  deleteIpoRI(id, type) {
    let userId = localStorage.getItem("userId");
    let url = environment.endPointURL + "stock/deleteIpoRI/" + id + "/" + userId;
    return this.http.delete(url);
  }
  loadDIOPAndIRToTable(userId: string) {
    let url = environment.endPointURL + "stock/getAllIpoRIByUser/" + userId;
    return this.http.get(url);
  }
  saveIpoRI(data: any) {
    let url = environment.endPointURL + "stock/saveIpoRI";
    return this.http.post(url, data);
  }

  constructor(private http: HttpClient) { }
}
