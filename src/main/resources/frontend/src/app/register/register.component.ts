import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ServiceService } from '../services/service.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public registerForm: FormGroup;
  formValid: boolean = true;

  firstNamePattern = "^[A-Z][a-zA-Z]*$";
  lastNamePattern = "^[A-Z][a-zA-Z]*$";
  emailPattern = "^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$";
  usernamePattern = "^[A-Za-z][A-Za-z0-9_]{7,29}$";
  passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\]).{8,32}$";
  phoneNumberPattern = "^[0-9]{10}$";

  constructor(
    private router: Router,
    private service: ServiceService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.registerForm = new FormGroup(
      {
        firstName: new FormControl('', [Validators.required, Validators.pattern(this.firstNamePattern)]),
        lastName: new FormControl('', [Validators.required, Validators.pattern(this.lastNamePattern)]),
        emailAddress: new FormControl('', [Validators.required, Validators.pattern(this.emailPattern)]),
        username: new FormControl('', [Validators.required, Validators.pattern(this.usernamePattern)]),
        password: new FormControl('', [Validators.required, Validators.pattern(this.passwordPattern), Validators.minLength(6)]),
        phoneNumber: new FormControl('', [Validators.required, Validators.pattern(this.phoneNumberPattern)]),
      }
    )
  }

  submitForm() {
    const userDetails = {
      firstName: this.registerForm.get('firstName').value,
      lastName: this.registerForm.get('lastName').value,
      emailAddress: this.registerForm.get('emailAddress').value,
      username: this.registerForm.get('username').value,
      password: this.registerForm.get('password').value,
      phoneNumber: this.registerForm.get('phoneNumber').value,

    };
    console.log(userDetails);
    this.service.onRegister(userDetails).subscribe(
      response => {
        console.log('response-body', response);
        this.router.navigate(['/login']);
        this.toastr.success(response['message']);
      }
    );
  }

  get u() {
    return this.registerForm.controls;
  }
}
