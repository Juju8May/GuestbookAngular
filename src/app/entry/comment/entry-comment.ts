import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Entry } from '../../entity/entry';
import { EntryService } from '../../service/entry-service';

interface EntryCommentModel {
    userId: number;
    noteId: number;
    note: string;
    time: Date;
}

@Component({
    selector: 'comment',
    standalone: true,
    imports: [FormsModule],
    templateUrl: './comment.html',
    styleUrls: ['./comment.css'],
})
export class EntryComment {
    title = 'Kommentar hinzufügen';
    entryId = 0;
    userId = 0;
    entry: Entry | undefined;
    comments: EntryCommentModel[] = [];
    newComment: EntryCommentModel = {
        userId: 0,
        noteId: 0,
        note: '',
        time: new Date(),
    };

    constructor(
        public router: Router,
        private route: ActivatedRoute,
        private service: EntryService,
    ) {
        this.entryId = Number(this.route.snapshot.paramMap.get('id'));
        this.entry = this.service.findEntryById(this.entryId);
    }

    navigateToOverview(): void {
        this.router.navigate(['/overview']);
    }

    navigateToEdit(): void {
        this.router.navigate(['/edit', this.entryId]);
    }

    addComment(newComment: EntryCommentModel): void {
        if (!this.entry) {
            return;
        }
        this.entry.comments.push(newComment);
        this.service.saveEntry(this.entry);
    }
}