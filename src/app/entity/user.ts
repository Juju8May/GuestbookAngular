import { EmailValidator } from "@angular/forms";
import { email } from "@angular/forms/signals";

export interface User {
    id: number,
    firstname: string,
    lastname: string,   
    email: string,
}