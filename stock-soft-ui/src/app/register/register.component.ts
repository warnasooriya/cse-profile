import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'app/services/auth.service';
import { Validators } from '@angular/forms';
import { MustMatch } from 'app/_helper/must-match.validator';
import Swal from 'sweetalert2';
import { environment } from 'environments/environment';

@Component({
  selector: 'ngx-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  submitted = false;
  await=false;
  fileData: File = null;
  awaiturl: any = null;
  errorData: String;
  formControlName: any;
  previewUrl: any = null;
	submitError = false;
  constructor( private formBuilder:FormBuilder,private aouthService:AuthService, private router: Router) { }
  errorText="";
  registerForm:FormGroup;
  ngOnInit(): void {

    this.registerForm = this.formBuilder.group({
      name:['',Validators.required],
      emailAddress: ['',Validators.required],
      mobileNo:['',Validators.required],
      address:['',Validators.required],
      username:['',Validators.required],
      password:['',Validators.required],
      cpass:['',Validators.required],
      cdsaccount:''
    },
    {
      validator: MustMatch('password', 'cpass')
  }
    );

  }

  get f() { return this.registerForm.controls; }

  onSubmit(){
    console.log('submitting');
    this.submitted = true;
    // stop here if form is invalid
    if (this.registerForm.invalid) {
      console.log(this.f);
      return;
    }else{

      this.aouthService.register(this.registerForm.value).subscribe(
        data => {
          Swal.fire('CSE Profile', 'Successfully Registered ', 'success');
          this.clearForm();
          this.submitted=false;
          this.router.navigate(['/auth']);
        },
        err => {
          Swal.fire('CSE Profile', 'Problem with Registration Please try again later', 'error');
          
        },
        () => {
            console.log("Complete function triggered.")
        }
      );



    }


  } 
  
  clearForm() {
    this.formBuilder.group({
      name:'',
      emailAddress: '' ,
      mobileNo:'' ,
      address:'' ,
      username:'' ,
      password:'' ,
      cpass:'',
      cdsaccount:''
    });
  }
}
