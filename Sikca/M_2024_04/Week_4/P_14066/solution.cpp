#include <bits/stdc++.h>
#define sz(x) (int)x.size()
using namespace std;
char arr[3007][3007] = {0};
int minx = INT_MAX;
int maxx = 0;
int miny = INT_MAX;
int maxy = 0;
void cube(int x, int y){
    minx = min(minx,x);
    miny = min(miny,y-2);
    maxx = max(maxx,x+5);
    maxy = max(maxy,y+4);
    arr[x][y] = '+';
    for (int i = 1; i <= 3; i++){
        arr[x][y+i] = '-';
        arr[x+2][y+i-2] = '-';
        arr[x+5][y+i-2] = '-';
    }
    for (int i = 0; i < 2; i++){
        arr[x+3+i][y-2] = '|';
        arr[x+3+i][y+2] = '|';
        arr[x+1+i][y+4] = '|';
    }
    arr[x+2][y-2] = '+';
    arr[x+2][y+2] = '+';
    arr[x+5][y+2] = '+';
    arr[x+5][y-2] = '+';
    arr[x][y+4] = '+';
    arr[x+3][y+4] = '+';
    for (int i = 0; i < 3; i++){
        arr[x+1][y+i] = ' ';
        arr[x+3][y+i-1] = ' ';
        arr[x+4][y+i-1] = ' ';
    }
    for (int i = 0; i < 2; i++){
        arr[x+2+i][y+3] = ' ';
    }
    arr[x+1][y-1] = '/';
    arr[x+1][y+3] = '/';
    arr[x+4][y+3] = '/';
    
}
int main(void) {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    //확실한 생각과 계획 코드
    for (int i = 0; i < 2007; i++){
        for (int j = 0; j < 2007; j++){
            arr[i][j] = '.';
        }
    }
    int sx = 1000;
    int sy = 1000;
    int n,m;
    cin >> n >> m;
    for (int i = 0; i < n; i++){
        int tx = sx;
        int ty = sy;
        for (int j = 0; j < m; j++){
            int a;
            cin >> a;
            int tmpx = sx;
            int tmpy = sy;
            for (int k = 0; k < a; k++){
                cube(tmpx,tmpy);
                tmpx -= 3;
            }
            sy += 4;
        }
        sx = tx + 2;
        sy = ty - 2;
    }
    for (int i = minx; i <= maxx; i++){
        for (int j = miny; j <= maxy; j++){
            cout << arr[i][j];
        }
        cout << '\n';
    }
    return 0;
}