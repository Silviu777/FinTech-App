import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app.routing';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'angular2-chartjs';

import { AppComponent } from './app.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './shared/footer/footer.component';

import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { AccountComponent } from './account/account.component';
import { TransferMoneyComponent } from './transfer-money/transfer-money.component';
import { TransactionHistoryComponent } from './transaction-history/transaction-history.component';
import { LoginComponent } from './login/login.component';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AuthGuardService } from './guard/auth-guard.service';
import { LoginGuardService } from './guard/login-guard.service';
import { TransactionChartComponent } from './transaction-chart/transaction-chart.component';


@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        NavbarComponent,
        FooterComponent,
        RegisterComponent,
        HomeComponent,
        UserProfileComponent,
        AccountComponent,
        TransferMoneyComponent,
        TransactionHistoryComponent,
        TransactionChartComponent
    ],
    imports: [
        BrowserModule,
        ChartModule,
        NgbModule,
        CommonModule,
        BrowserAnimationsModule,
        FormsModule,
        RouterModule,
        AppRoutingModule,
        ReactiveFormsModule,
        HttpClientModule,
        ToastrModule.forRoot()
    ],
    providers: [
        AuthGuardService, LoginGuardService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {

}
