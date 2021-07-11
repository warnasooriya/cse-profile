import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'app/services/auth.service';

@Component({
  selector: 'ngx-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {


  loginForm: FormGroup;
  constructor(private formBuilder: FormBuilder, private aouthService: AuthService, private router: Router) { }
  errorText = "";
  ngOnInit(): void {
    var un = localStorage.getItem('remember_username');
    var pw = localStorage.getItem('remember_pw');

    var unDec = "";
    var pwDec = "";
    if (un != null && un != undefined) {
      unDec = window.atob(un);
      pwDec = window.atob(pw);
    }

    this.loginForm = this.formBuilder.group({
      username: unDec,
      password: pwDec,
      remember: true
    });

  }

  register() {
    console.log('register call');
    this.router.navigate(['/auth/register']);
  }

  onSubmit() {
    this.errorText = "";
    if (this.loginForm.value.username == "") {
      this.errorText = "Username Required";
      return true;
    }
    if (this.loginForm.value.password == "") {
      this.errorText = "Password Required";
      return true;
    }
    var remember = this.loginForm.value.remember;
    if (remember) {
      var unEn = window.btoa(this.loginForm.value.username);
      var pwEn = window.btoa(this.loginForm.value.password);
      localStorage.setItem('remember_username', unEn);
      localStorage.setItem('remember_pw', pwEn);
    } else {
      localStorage.removeItem('remember_username');
      localStorage.removeItem('remember_pw');
    }

    this.aouthService.login(this.loginForm.value).subscribe(
      data => {
        console.log('success')
        localStorage.setItem("access_token", data['access_token']);
        localStorage.setItem("refresh_token", data['refresh_token']);

        this.redirectDashboard();
      },
      err => {
        console.log('error');
        this.errorText = "Invalied Credencial";
        console.log(err)
      },
      () => {
        console.log("Complete function triggered.")
      }
    );
  }

  redirectDashboard() {
    this.aouthService.getUser(this.loginForm.value.username).subscribe(
      data => {
        localStorage.setItem("userId", data['id']);
        localStorage.setItem("csePUsername", this.loginForm.value.username);
        localStorage.setItem("cseEmail", data['emailAddress']);
        localStorage.setItem("csePicture", data['picture']);

        this.router.navigate(['pages/dashboard']);
      },
      err => {
        console.log('error');
        this.errorText = "Invalied Credencial";
        console.log(err)
      },
      () => {
        console.log("Complete function triggered.")
      }
    );
  }

}
