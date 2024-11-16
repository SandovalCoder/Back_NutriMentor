export interface User {
    id: number;
    username: string;
    password: string;
    active: boolean;
    clientId?: number;
    authorities: string;
}

