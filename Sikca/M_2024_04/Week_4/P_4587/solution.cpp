#include <bits/stdc++.h>
using namespace std;
typedef long long int ll;
ll gcd(ll a, ll b) {
	if (b == 0) {
		return a;
	}
	return gcd(b, a % b);
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	cout.tie(0);
	while (1) {
		ll n, m;
		cin >> n >> m;
		if (n == 0 && m == 0) return 0;
		ll cur = 2;
		while (1) {
			if (n == 1) {
				cout << m << '\n';
				break;
			}
			ll tmpn = n;
			ll tmpm = m;
			ll cd = gcd(m, cur); 
			ll lcm = (m * cur) / cd;
			n = n * (lcm / m);
			m = lcm;
			n -= (lcm / cur);
			if (n <= 0) {
				n = tmpn;
				m = tmpm;
				cur++;
				continue;
			}
			ll cd2 = gcd(n, m);
			n /= cd2;
			m /= cd2;
			if (m >= 1000000) {
				n = tmpn;
				m = tmpm;
				cur++;
			}
			else {
				cout << cur << ' ';
			}
		}

	}
	return 0;
}