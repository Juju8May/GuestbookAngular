import { Routes } from '@angular/router';
import { App } from '../app/app';
import { Overview } from '../overview/overview';
import { Home } from '../home/home';

export const routes: Routes = [
     {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full',
    },
    {
        path: 'home',
        title: 'Startseite',
        component: Home,
    },
    {
        path: 'overview',
        title: 'Übersicht',
        component: Overview,
    },
];
