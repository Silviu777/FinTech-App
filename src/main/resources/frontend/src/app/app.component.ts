import { Component, OnInit, Inject, Renderer2, ElementRef, ViewChild } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import 'rxjs/add/operator/filter';
import { DOCUMENT } from '@angular/common';
import { LocationStrategy, PlatformLocation, Location } from '@angular/common';
import { NavbarComponent } from './shared/navbar/navbar.component';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
    private _router: Subscription;
    @ViewChild(NavbarComponent) navbar: NavbarComponent;

    constructor(private renderer: Renderer2, private router: Router, @Inject(DOCUMENT,) private document: any, private element: ElementRef, public location: Location) {
    }

    ngOnInit() {
        var navbar: HTMLElement = this.element.nativeElement.children[0].children[0];
        this._router = this.router.events.filter(event => event instanceof NavigationEnd).subscribe((event: NavigationEnd) => {
            if (window.outerWidth > 991) {
                window.document.children[0].scrollTop = 0;
            } else {
                window.document.activeElement.scrollTop = 0;
            }
            this.navbar.sidebarClose();
        });

        this.renderer.listen('window', 'scroll', (event) => { });

        const nav = window.navigator.userAgent;
        const trident = nav.indexOf('Trident/');

        if (trident > 0) {
            const rv = nav.indexOf('rv:');
            var version = parseInt(nav.substring(rv + 3, nav.indexOf('.', rv)), 10);
        }
        if (version) {
            const body = document.getElementsByTagName('body')[0];
            body.classList.add('ie-background');

        }
    }

    removeFooter() {
        let titlee = this.location.prepareExternalUrl(this.location.path());
        titlee = titlee.slice(1);
        return !(titlee === '/signup' || titlee === '/nucleoicons' || titlee === '/login' || titlee === '/register');
    }
}
