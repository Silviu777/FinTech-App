import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../services/service.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  public email: string;
  public firstName: string;
  public lastName: string;
  public username: string;
  public phoneNumber: string;
  public initialized: boolean;

  constructor(
    private service: ServiceService,
    private toastr: ToastrService,
  ) {
    this.initialized = false;
  }

  ngOnInit(): void {
    this.service.refreshNeeded$.subscribe(() => {
      this.fetchProfile();
    });
    if (!this.initialized) {
      this.fetchProfile();
      this.initialized = true;
    }
  }

  fetchProfile() {
    this.service.onFetchProfile().subscribe(
      response => {
        this.email = response['emailAddress'];
        this.firstName = response['firstName'];
        this.lastName = response['lastName'];
        this.phoneNumber = response['phoneNumber'];
        this.username = response['username'];
      }
    );
  }

  updateProfile() {
    const userDetails = {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      phoneNumber: this.phoneNumber
    };
    console.log(userDetails);
    this.service.onUpdateProfile(userDetails).subscribe(
      response => {
        this.toastr.success(response['message']);
      }
    );
  }
}
