import { Routes } from '@angular/router';
import { Edit } from './app/entry/edit/edit';
import { App } from './app';
import { Overview } from './app/overview/overview';
import { EntryComment } from './app/entry/comment/entry-comment';
import { Home } from './app/home/home';

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
    {
        path: 'edit/:id',
        title: 'Eintrag bearbeiten',
        component: Edit,
    },
    {
        path: 'comment/:id',
        title: 'Kommentar hinzufügen',
        component: EntryComment,
    }
];
