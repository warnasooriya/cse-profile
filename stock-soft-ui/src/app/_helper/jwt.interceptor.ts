import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from 'environments/environment';
import { AuthService } from 'app/services/auth.service';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    constructor(private router: Router) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        var url = request['url'];
        //console.log(url);
        if(url.includes("oauth/token")==false && url.includes("logout")==false  && url.includes("register")==false && url.includes("landing")==false){
            var token =  localStorage.getItem('access_token');
            if(token==null){
                token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDk4Njc1MTQsInVzZXJfbmFtZSI6InJhdmkiLCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJqdGkiOiIwOWQ0NjA1YS1kMDMyLTQ1NTYtODA0MS1iODI1MGE4OWEyMmMiLCJjbGllbnRfaWQiOiJjc2UtY2xpZW50Iiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.kGLTyqHE95YmHzY_OJNPwMWkt9q5vydPglBOeGYgGno";
            } 
                request = request.clone({
                    setHeaders: {
                        Authorization: `Bearer ${token}`
                    }
                });
        }

        //return next.handle(request);
        return next.handle(request).pipe( tap(() => {},
      (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status !== 401) {
         return;
        }else{
            this.router.navigate(['auth']);
        }
       
      }
    }));
    }
}