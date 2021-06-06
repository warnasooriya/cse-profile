import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WidthdrawService {
  getPreviousWidthdrws() {
    let userId = localStorage.getItem("userId");
    //let url = environment.endPointURL + "stock/getPreviousWidthdrws/" + userId;
    let params = new HttpParams()
      .set('alphabet', 'A');
    let urlNw = environment.cseEntPoint + "alphabetical";
    return this.http.post(urlNw, params);

    //return this.http.get(url);

  }
  deleteWidthdrw(id: any) {
    let userId = localStorage.getItem("userId");
    let url = environment.endPointURL + "stock/deleteWidthdrw/" + id + "/" + userId;
    return this.http.delete(url);
  }
  loadWidthdrw(userId: string) {
    let url = environment.endPointURL + "stock/getAllWidthdrwByUser/" + userId;
    return this.http.get(url);
  }
  saveWidthdrw(data: any) {
    let url = environment.endPointURL + "stock/saveWidthdrw";
    return this.http.post(url, data);
  }

  constructor(private http: HttpClient) { }
}
