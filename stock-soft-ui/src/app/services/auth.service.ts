import { Injectable } from '@angular/core';
import { environment } from 'environments/environment';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

const OAUTH_CLIENT = 'cse-client';
const OAUTH_SECRET = 'cse-secret';


@Injectable({
  providedIn: 'root'
})

export class AuthService {

  uploadProfileImage(formData) {
    console.log('onupload');
    let url = environment.endPointURL + 'users/uploadProfilePhoto';
    return this.http.post(url, formData);
  }
  register(value) {
    let url = environment.endPointURL + "users/register";
    return this.http.post(url, value);
  }

  logout() {
    localStorage.removeItem('userId');
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');
  }
  getUser(username) {
    let url = environment.endPointURL + "users/getByUserName"
    return this.http.post(url, { "userName": username });
  }

  login(loginData) {
    var authData = window.btoa(OAUTH_CLIENT + ':' + OAUTH_SECRET);
    const headers = new HttpHeaders()
      .set('X-Requested-With', 'XMLHttpRequest')
      .set('Content-Type', 'application/json')
      .set('Authorization', 'Basic ' + authData);

    let url = environment.endPointURL + "oauth/token?grant_type=password&username=" + loginData.username + "&password=" + loginData.password;
    return this.http.post<any>(url, loginData, { headers: headers }).pipe(
      map((token) => {
        return token;
      }),
      //catchError(this.handleError<any>('login failier'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      // console.error(error); // log to console instead
      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  constructor(private http: HttpClient) { }
}
